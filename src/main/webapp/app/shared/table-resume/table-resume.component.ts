import { Component, OnInit, OnDestroy, Input, Output , EventEmitter, TemplateRef, AnimationKeyframe} from "@angular/core";
import { ENUM } from "./../enum";
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
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
    @Input() cellFunctionTemplate: TemplateRef<String>;
    @Output() cellFunction = new EventEmitter<AnimationKeyframe>();

    constructor(private ngbModal: NgbModal) {

    }

    ngOnInit() {
    }

    ngOnDestroy() {
    }

    cellClick(data) {
        debugger
        this.ngbModal.open(this.cellFunctionTemplate).result.then((result)=> {
            this.cellFunction.emit({result: result, data: data});
        }, (reason) => {
        });
    }

}