import { HttpErrorResponse } from "@angular/common/http";
import { Subscription, interval } from "rxjs";
import { MainComponent } from "../component/main/main.component";
import { Pomodoro } from "../entity/pomodoro";
import { Settings } from "../entity/settings";
import { PomodoroService } from "../service/pomodoro.service";
import { SettingsService } from "../service/settings.service";
import { Timer } from "./timer";

export class PomodoroTimer implements Timer {
    public current: number = 0;
    private interval?: Subscription;
    public started: boolean = false;
    public paused: boolean = false;
    pomodoroId?: number;
    public minutes: number;
    private sub?: Subscription;

    constructor(private component: MainComponent, 
    private service: PomodoroService, 
    private settings: SettingsService) {
        this.minutes = this.settings.defaultPomodoroLength;
        this.sub = this.settings.settingsPublisher.subscribe(
            (settings: Settings) => {
                this.minutes = settings.defaultPomodoroLength
            }
        );
     }

    start(): void {
        this.sub?.unsubscribe();
        this.service.start({
            deepFocus: false,
            collaborative: false,
            tagId: this.component.tag ? this.component.tag.id : null,
            minutes: this.minutes
        }).subscribe(
            (response: Pomodoro) => {
                this.pomodoroId = response.id;
                this.started = true;
                this.current = 0;
                this.interval = interval(1000).subscribe((x) => this.step());
            },
            (error: HttpErrorResponse) => {
                this.component.loadError(error);
            }
        );
    }

    private step(): void {
        if(this.current >= this.minutes*60) {
            this.stop();
        } else {
            this.current += 1;
        }
    }

    stop(): void {
    this.interval?.unsubscribe();
        if(this.pomodoroId) {
            this.service.stop(this.pomodoroId).subscribe(
                (response: Pomodoro) => {
                    this.started = false;
                    this.component.skip();
                },
                (error: HttpErrorResponse) => {
                    this.component.loadError(error);
                }
            );
        }
    }

    pause(): void {
        this.interval?.unsubscribe();
        if(this.pomodoroId) {
            this.service.pause(this.pomodoroId).subscribe(
                (response: Pomodoro) => {
                    this.paused = true;
                },
                (error: HttpErrorResponse) => {
                    this.component.loadError(error);
                }
            );
        }
    }

    restart(): void {
        if(this.pomodoroId) {
            this.service.restart(this.pomodoroId).subscribe(
            (response: Pomodoro) => {
                this.pomodoroId = response.id;
                this.paused = false;
                this.current = this.minutes*60 - response.secondsAfterPause;
                this.interval = interval(1000).subscribe((x) => this.step());
            },
            (error: HttpErrorResponse) => {
                this.component.loadError(error);
            }
            );
        }
    }

    cancel(): void {
        this.interval?.unsubscribe();
        if(this.pomodoroId) {
        this.service.cancel(this.pomodoroId).subscribe(
            (response: Pomodoro) => {
                this.started = false;
                this.current = 0;
                this.component.stop();
            },
            (error: HttpErrorResponse) => {
                this.component.loadError(error);
            }
        );
        } else {
            this.started = false;
            this.current = 0;
            this.component.stop();
        }
    }
}
