export interface PomodoroMin {
    id: number;
    failed: boolean;
    succeed: boolean;
    start: Date;
    finish: Date | undefined;
    minutes: number;
}