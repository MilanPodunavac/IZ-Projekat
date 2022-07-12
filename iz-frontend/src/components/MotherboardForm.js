import { useState } from "react";
import ComponentSelectBox from "./ComponentSelectBox";
import axios from 'axios';

const MotherboardForm = () => {
    const [cpu, setCpu] = useState();
    const [gpu, setGpu] = useState();
    const [ram, setRam] = useState();
    const [isPending, setIsPending] = useState(false);
    const [results, setResults] = useState();

    const cpuSelector = selected => setCpu(selected[0]);
    const gpuSelector = selected => setGpu(selected[0]);
    const ramSelector = selected => setRam(selected[0]);

    const onSubmit = async (e) => {
        e.preventDefault();
        setIsPending(true);
        const dto = {
            "cpu": cpu,
            "gpu": gpu,
            "ram": ram
        };
        setIsPending(true);
        axios.post(axios.defaults.baseURL + 'api/motherboard', dto).then(res => {
            setResults(res.data.results);
            setIsPending(false);
        }).catch((err) => { console.log(err) });
    }

    function isSubmitDisabled() {
        return false;
    }

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
                <div className="mb-3">
                    {!isPending &&
                        <span className="right">
                            <button disabled={isSubmitDisabled()} to="#" onClick={(e) => onSubmit(e)} type="submit" className="btn btn-primary">Submit</button>
                        </span>
                    }
                    {isPending && <label>Waiting for results...</label>}
                </div>
            </form>
        </>
    );
}

export default MotherboardForm;