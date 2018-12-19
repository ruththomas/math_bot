export class Event {
  constructor ({date, title, description, id = null, links}) {
    this.id = id
    this.date = new Date(date)
    this.title = title
    this.description = description
    this.links = links
  }
}
