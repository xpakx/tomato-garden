import { Subscription, interval } from "rxjs";
import { MainComponent } from "../component/main/main.component";
import { Timer } from "./timer";

export class BreakTimer implements Timer {
    public current: number = 0;
    private interval?: Subscription;
    started: boolean = false;
    paused: boolean = false;
    pomodoroId: undefined;

    constructor(private component: MainComponent, 
    public minutes: number) { }

    start() {
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