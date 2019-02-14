import { Component, OnInit, OnDestroy, Input, Output , EventEmitter} from "@angular/core";
import { ENUM } from "./../enum";
@Component({
    selector: 'table-resume',
    templateUrl: 'table-resume.component.html',
    styleUrls: ['table-resume.style.scss']
})

export class TableResume implements OnInit, OnDestroy{

    @Input() resumeTitle: string;
    @Input() rowHeader: any[];
    @Input() columnList: any[]; 
    @Input() dataList: any[]; 

    ngOnInit() {
    }

    ngOnDestroy() {
    }

}