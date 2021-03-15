import { FormGroup } from '@angular/forms';

export function ConfirmedValidator(
    fieldName: string,
    matchingFieldName: string
) {
    return (group: FormGroup) => {
        const field = group.get(fieldName).value;
        const fieldConf = group.get(matchingFieldName).value;

        if (fieldConf.errors && !fieldConf.errors.notSame) {
            return;
        }

        if (field !== fieldConf) {
            group.controls[matchingFieldName].setErrors({ 'notSame': true });
        } else {
            group.controls[matchingFieldName].setErrors(null);
        }
    };
};
