import { Component, OnInit, OnDestroy, Input, Output , EventEmitter} from "@angular/core";
@Component({
    selector: 'box-info',
    templateUrl: 'box-info.component.html'
})

export class BoxInfo implements OnInit, OnDestroy{
    @Input() number: number;
    @Input() text: string;
    ngOnInit() {
        if (!this.number) {
            this.number = 0;
        }
    }

    onChange() {
    }

    ngOnDestroy() {
    }

}