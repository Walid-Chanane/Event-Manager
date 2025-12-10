import { EventFileResponse } from "./event-file-response";

export interface EventResponse {
    id: number,
    title: string,
    description: string,
    time: Date,
    location: string,
    status: string,
    files: Array<EventFileResponse>,
}
