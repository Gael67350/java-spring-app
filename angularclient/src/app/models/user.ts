export class User {
    id?: number;
    firstName: string;
    lastName: string;
    mail: string;
    password?: string;
    authdata?: string;

    constructor() {
        this.firstName = '';
        this.lastName = '';
        this.mail = '';
    }
}
