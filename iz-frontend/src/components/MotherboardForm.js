import { useState } from "react";
import ComponentSelectBox from "./ComponentSelectBox";
import axios from 'axios';
import MotherboardTable from "./MotherboardTable";

const MotherboardForm = () => {
    const [cpu, setCpu] = useState();
    const [cooler, setCooler] = useState();
    const [ram, setRam] = useState();
    const [isPending, setIsPending] = useState(false);
    const [results, setResults] = useState();

    const cpuSelector = selected => setCpu(selected[0]);
    const coolerSelector = selected => setCooler(selected[0]);
    const ramSelector = selected => setRam(selected[0]);

    const onSubmit = async (e) => {
        e.preventDefault();
        setIsPending(true);
        axios.get(axios.defaults.baseURL + 'api/Motherboard/Compatible/'+ cpu + '/'+ cooler + '/' + ram).then(res => {
            setResults(res.data);
            console.log(res.data)
            setIsPending(false);
        }).catch((err) => { 
            console.log(err);
            setIsPending(false);
        });
    }

    function isSubmitDisabled() {
        return !cpu || !ram || !cooler;
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
                    <label className="form-label">Choose cooler:</label>
                    <ComponentSelectBox
                        setSelectedValue={coolerSelector}
                        path={"api/common/allCoolers"}
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
            {
                results && <MotherboardTable results={results}/>
            }
        </>
    );
}

export default MotherboardForm;