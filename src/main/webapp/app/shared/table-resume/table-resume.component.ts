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
    @Input() atributeToDisplay: string;
    @Input() cellFunctionTemplate: TemplateRef<String>;
    @Output() cellFunction = new EventEmitter<AnimationKeyframe>();
    verticalTotal: any[];
    horizontalTotal: any[];
    somVerticalTotal: number = 0;

    constructor(private ngbModal: NgbModal) {
    }

    ngOnInit() {
        this.verticalTotal = [];
        this.horizontalTotal = [];
        this.dataList.forEach((data) => {
            if (this.verticalTotal[data.produto.tamanho] == null) {
                this.verticalTotal[data.produto.tamanho] = 0;
                this.verticalTotal[data.produto.tamanho] += data[this.atributeToDisplay];
                this.somVerticalTotal += data[this.atributeToDisplay];
            }else {
                this.verticalTotal[data.produto.tamanho] += data[this.atributeToDisplay];
                this.somVerticalTotal += data[this.atributeToDisplay];
            }

            if (this.horizontalTotal[data.produto.cor.id] == null) {
                this.horizontalTotal[data.produto.cor.id] = 0;
                this.horizontalTotal[data.produto.cor.id] += data[this.atributeToDisplay];
            }else {
                this.horizontalTotal[data.produto.cor.id] += data[this.atributeToDisplay];
            }

        });
    }

    ngOnDestroy() {

    }

    cellClick(data) {
        if (this.cellFunction.observers.length > 0) {
            this.ngbModal.open(this.cellFunctionTemplate).result.then((result)=> {
                this.cellFunction.emit({result: result, data: data});
            }, (reason) => {
            });
        }
    }

    subTotal (array) {
        debugger
        if (array && array.lenght > 0) {
            return array.reduce((a,b) => {return a+b}, 0);
        }
        return "";
    }

}