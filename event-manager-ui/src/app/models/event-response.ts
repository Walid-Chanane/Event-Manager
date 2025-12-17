import { EventFileResponse } from "./event-file-response";
import { Participant } from "./participant";

export interface EventResponse {
    id: number,
    title: string,
    description: string,
    time: string,
    location: string,
    status: string,
    files: Array<EventFileResponse>,
    participantIDs? : Array<number>
}
