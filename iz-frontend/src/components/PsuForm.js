import { useState } from "react";
import ComponentSelectBox from "./ComponentSelectBox";
import axios from 'axios';
import PsuTable from "./PsuTable";

const PsuForm = () => {
    const [cpu, setCpu] = useState();
    const [gpu, setGpu] = useState();
    const [isPending, setIsPending] = useState(false);
    const [results, setResults] = useState();

    const cpuSelector = selected => setCpu(selected[0]);
    const gpuSelector = selected => setGpu(selected[0]);

    const onSubmit = async (e) => {
        e.preventDefault();
        setIsPending(true);
        const dto = {
            //"cpu": cpu,
            "gpuName": gpu
        };
        axios.post(axios.defaults.baseURL + 'api/Psu', dto).then(res => {
            console.log(res.data);
            setResults(res.data);
            setIsPending(false);
        }).catch((err) => {
            console.log(err);
            setIsPending(false);
        });
    }

    function isSubmitDisabled() {
        return false;
    }

    return (
        <>
            <hr />
            <form style={{ maxWidth: "50%", margin: "auto" }}>
                {/*<div className="mb-3">
                    <label className="form-label">Choose CPU:</label>
                    <ComponentSelectBox
                        setSelectedValue={cpuSelector}
                        path={"api/common/allCpu"}
                        message={""}
                        propertyName={'componentShortDtoList'}
                        singleSelect={true} />
    </div>*/}
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
                    {!isPending &&
                        <span className="right">
                            <button disabled={isSubmitDisabled()} to="#" onClick={(e) => onSubmit(e)} type="submit" className="btn btn-primary">Submit</button>
                        </span>
                    }
                    {isPending && <label>Waiting for results...</label>}
                </div>
            </form>
            {
                results && <PsuTable results={results} />
            }
        </>
    );
}

export default PsuForm;