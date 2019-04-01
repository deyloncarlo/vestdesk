import { OnInit, OnDestroy, Component, Input, Output, EventEmitter } from "@angular/core";
import { ENUM } from "../enum"

@Component({
    selector: 'filter-form',
    templateUrl: 'filter-form.component.html',
    styleUrls: ['filter-form.style.scss']
})

export class FilterForm implements OnInit, OnDestroy {

    /** Amount of settings to configure the component */
    @Input() settings: any;

    /** List of all fields configurations that will be in the FilterForm */
    @Input() fieldList: any[];

    /** Array with all datas written on filelds */
    @Input() filterData: {};

    @Output() toggledChange = new EventEmitter<any>();

    @Output() filterCallback = new EventEmitter<any>();

    private enumList: any;

    ngOnInit() {
        this.settings = (this.settings == null || this.settings == undefined) ? this.getDefualtSettings() : this.settings;
        this.fieldList = this.fieldList == null || this.fieldList == undefined ? [] : this.fieldList;
        this.filterData = this.filterData == null || this.filterData == undefined ? {} : this.filterData;
        this.enumList = ENUM;
        this.tidyFilterData();
    }

    ngOnDestroy() {

    }

    private getDefualtSettings() {
        return {
            hideFilterButton: false,
            filterOnFieldValueChange: false
        };
    }

    onFieldChange(field) {
        this.toggledChange.emit(this.filterData);
        if (this.settings.filterOnFieldValueChange) {
            this.filter();
        }
    }

    tidyFilterData() {
        this.fieldList.forEach((field) => {
            if (field.type == "select") {
                this.filterData[field.name] = "";
            }
            else if (this.filterData[field.name] == undefined) {
                this.filterData[field.name] = "";
            }
        });
        this.toggledChange.emit(this.filterData);
    }
    
    filter () {
        this.filterCallback.emit();
    }
}