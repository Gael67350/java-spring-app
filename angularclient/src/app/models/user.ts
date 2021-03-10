export class User {
    id?: number;
    firstName: string;
    lastName: string;
    mail: string;

    constructor() {
        this.firstName = '';
        this.lastName = '';
        this.mail = '';
    }
}
