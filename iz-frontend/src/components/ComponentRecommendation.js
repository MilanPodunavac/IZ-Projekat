import { useState } from "react";
import MultiSelectBox from "./MultiSelectBox";
import MotherboardForm from "./MotherboardForm";
import PsuForm from "./PsuForm";
import RamForm from "./RamForm";
import CpuForm from "./CpuForm";
import GpuForm from "./GpuForm";
import StorageForm from "./StorageForm";

const ComponentRecommendation = () => {
    const [componentType, setComponentType] = useState([]);

    const componentTypeSelector = type => setComponentType(type[0]);

    return (
        <div className="container align-content: center display: flex align-items: center mt-5">
            <form style={{ maxWidth: "50%", margin: "auto" }}>
                <div className="mb-3">
                    <label className="form-label">Choose component for upgrade</label>
                    <MultiSelectBox
                        selectedValue={componentType}
                        setSelectedValue={componentTypeSelector}
                        path={"api/common/componentTypes"}
                        message={""}
                        propertyName={'componentTypesList'} 
                        singleSelect={true}/>
                </div>
            </form>
            {componentType === 'Motherboard' && <MotherboardForm/>}
            {componentType === 'PowerSupplyUnit' && <PsuForm/>}
            {componentType === 'RandomAccessMemory' && <RamForm/>}
            {componentType === 'CentralProcessingUnit' && <CpuForm/>}
            {componentType === 'GraphicsProcessingUnit' && <GpuForm/>}
            {componentType === 'Storage' && <StorageForm/>}
        </div>
    );
}

export default ComponentRecommendation;