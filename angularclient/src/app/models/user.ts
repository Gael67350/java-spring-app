export class User {
    id?: number;
    firstName: string;
    lastName: string;
    mail: string;
    password?: string;
    token?: string;
    authdata?: string;

    constructor(
        id: number|null,
        firstName: string,
        lastName: string,
        mail: string,
        password: string|null
        ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
    }
}
