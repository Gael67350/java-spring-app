export class User {
    id?: number;
    firstName: string;
    lastName: string;
    mail: string;
    token: string;
    authdata?: string;

    constructor() {
        this.firstName = '';
        this.lastName = '';
        this.mail = '';
        this.token = '';
    }
}
