import { useState } from "react";
import ComponentSelectBox from "./ComponentSelectBox";

const MotherboardForm = () => {
    const [cpu, setCpu] = useState();
    const [gpu, setGpu] = useState();
    const [ram, setRam] = useState();

    const cpuSelector = selected => setCpu(selected[0]);
    const gpuSelector = selected => setGpu(selected[0]);
    const ramSelector = selected => setRam(selected[0]);

    return (
        <>
            <hr />
            <form style={{ maxWidth: "50%", margin: "auto" }}>
                <div className="mb-3">
                    <label className="form-label">Choose CPU:</label>
                    <ComponentSelectBox
                        setSelectedValue={cpuSelector}
                        path={"api/common/allCpu"}
                        message={""}
                        propertyName={'componentShortDtoList'}
                        singleSelect={true} />
                </div>
                <div className="mb-3">
                    <label className="form-label">Choose GPU:</label>
                    <ComponentSelectBox
                        setSelectedValue={gpuSelector}
                        path={"api/common/allGpu"}
                        message={""}
                        propertyName={'componentShortDtoList'}
                        singleSelect={true} />
                </div>
                <div className="mb-3">
                    <label className="form-label">Choose RAM:</label>
                    <ComponentSelectBox
                        setSelectedValue={ramSelector}
                        path={"api/common/allRam"}
                        message={""}
                        propertyName={'componentShortDtoList'}
                        singleSelect={true} />
                </div>
            </form>
        </>
    );
}

export default MotherboardForm;