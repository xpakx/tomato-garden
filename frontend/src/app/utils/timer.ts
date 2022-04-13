export interface Timer {
    minutes: number;
    current: number;
    paused: boolean;
    started: boolean;

    start(): void;
    stop(): void;
    cancel(): void;
    pause(): void;
    restart(): void;
}
