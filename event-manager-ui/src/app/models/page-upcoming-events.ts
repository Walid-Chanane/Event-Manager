import { EventResponse } from "./event-response";

export interface PageUpcomingEvents {
  content?: Array<EventResponse>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}