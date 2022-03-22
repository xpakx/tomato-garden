import { Subscription, interval } from "rxjs";
import { MainComponent } from "../component/main/main.component";
import { Settings } from "../entity/settings";
import { SettingsService } from "../service/settings.service";
import { Timer } from "./timer";

export class BreakTimer implements Timer {
    public current: number = 0;
    private interval?: Subscription;
    started: boolean = false;
    paused: boolean = false;
    pomodoroId: undefined;
    public minutes: number;
    private sub?: Subscription;

    constructor(private component: MainComponent, private settings: SettingsService) { 
        this.minutes = this.settings.defaultBreakLength;
        this.sub = this.settings.settingsPublisher.subscribe(
            (settings: Settings) => {
                this.minutes = settings.defaultBreakLength
            }
        );
    }

    start() {
        this.sub?.unsubscribe();
        this.started = true;
        this.current = 0;
        this.interval = interval(1000).subscribe((x) => this.step());
    }

    step(): void {
        if(this.current >= this.minutes*60) {
          this.stop();
        } else {
          this.current += 1;
        }
    }

    stop(): void {
        this.interval?.unsubscribe();
        this.started = false;
        this.component.skip();
    }

    pause(): void {
        this.interval?.unsubscribe();
        this.paused = true;
    }

    restart(): void {
        this.paused = false;
        this.interval = interval(1000).subscribe((x) => this.step());
    }

    cancel(): void {
        this.interval?.unsubscribe();
        this.started = false;
        this.current = 0;
        this.component.skip();
    }
}