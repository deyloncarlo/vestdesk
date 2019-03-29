import { Component, OnDestroy, OnInit, Input, Output, AnimationKeyframe } from "@angular/core";
import { EventEmitter } from "events";

@Component({
    selector: "item-viewer",
    templateUrl: "item-viewer.component.html",
    styleUrls: ["item-viewer.style.scss"]
})

export class ItemViewer implements OnInit, OnDestroy {

    @Input() list: any[];
    @Input() attributeToDisplay: string;
    @Input() attributeToSetRead: string;
    @Input() onItemClickFunction: Function;

    private listClickedItem: any[];

    ngOnInit() {
        this.list = this.list == null || this.list == undefined ? [] : this.list;
        this.listClickedItem = [];
    }

    ngOnDestroy() {

    }

    async onClickItemView (item) {
        if (this.listClickedItem.indexOf(item) == -1) {
            item[this.attributeToSetRead] = true;
            this.listClickedItem.push(item);
            if (this.onItemClickFunction) {
                let promise: Promise<any> = this.onItemClickFunction(this.listClickedItem);
            }
        }

    }
}