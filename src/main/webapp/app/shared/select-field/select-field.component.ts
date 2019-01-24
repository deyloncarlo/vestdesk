import { Component, OnInit, OnDestroy, Input, Output , EventEmitter} from "@angular/core";
import { ENUM } from "./../enum";
@Component({
    selector: 'select-field',
    templateUrl: 'select-field.component.html'
})

export class SelectField implements OnInit, OnDestroy{
    @Input() enum: string;
    @Input() model: string;
    @Input() fieldName: string;
    @Output() toggledChange = new EventEmitter<string>();

    private lista = [null];
    ngOnInit() {

        if (this.enum != null) {
            this.lista = this.lista.concat(ENUM[this.enum]);
        }

    }

    onChange() {
        debugger
        this.toggledChange.emit(this.model);
    }

    ngOnDestroy() {
    }

}