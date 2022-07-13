import { useState } from "react";
import axios from 'axios';
import ComponentSelectBox from "./ComponentSelectBox";
import SimilarityTable from "./SimilarityTable";
import CompatibilityTable from "./CompatibilityTable";

const ChooseComponents = ({ useCase }) => {
    const [isPending, setIsPending] = useState(false);
    const [results, setResults] = useState();
    const [motherboard, setMotherboard] = useState();
    const [psu, setPsu] = useState();
    const [cpu, setCpu] = useState();
    const [gpu, setGpu] = useState();
    const [ram, setRam] = useState();
    const [storage, setStorage] = useState();

    const cpuSelector = selected => setCpu(selected[0]);
    const gpuSelector = selected => setGpu(selected[0]);
    const ramSelector = selected => setRam(selected[0]);
    const motherboardSelector = selected => setMotherboard(selected[0]);
    const psuSelector = selected => setPsu(selected[0]);
    const storageSelector = selected => setStorage(selected[0]);

    const onSubmit = async (e) => {
        e.preventDefault();
        setIsPending(true);
        if (useCase === 'compatibility') {
            const dto = {
                //"motherboard": motherboard,
                //"psu": psu,
                "cpuName": cpu,
                "gpuName": gpu,
                "ramName": ram,
                "diskName": storage
            };
            setIsPending(true);
            axios.put(axios.defaults.baseURL + 'api/Fuzzy', dto).then(res => {
                setResults(res.data);
                console.log(Object.keys(res.data));
                setIsPending(false);
            }).catch((err) => {
                console.log(err);
                setIsPending(false);
            });
        } else {
            const dto = {
                "mbName": motherboard,
                //"psu": psu,
                "cpuName": cpu,
                "gpuName": gpu,
                "ramName": ram,
                "diskName": storage
            };
            setIsPending(true);
            axios.post(axios.defaults.baseURL + 'api/CaseBasedReasoning', dto).then(res => {
                setResults(res.data);
                setIsPending(false);
            }).catch((err) => {
                console.log(err);
                setIsPending(false);
            });
        }
    }

    function isSubmitDisabled() {
        return false;
    }

    return (
        <div className="container align-content: center display: flex align-items: center mt-5">
            <form style={{ maxWidth: "50%", margin: "auto" }}>
                {useCase !== 'compatibility' &&
                    <div className="mb-3">
                        <label className="form-label">Choose motherboard:</label>
                        <ComponentSelectBox
                            setSelectedValue={motherboardSelector}
                            path={"api/common/allMotherboards"}
                            message={""}
                            propertyName={'componentShortDtoList'}
                            singleSelect={true} />
                    </div>
                }
                {/*<div className="mb-3">
                    <label className="form-label">Choose power supply unit:</label>
                    <ComponentSelectBox
                        setSelectedValue={psuSelector}
                        path={"api/common/allPsu"}
                        message={""}
                        propertyName={'componentShortDtoList'}
                        singleSelect={true} />
    </div>*/}
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
                    <label className="form-label">Choose storage:</label>
                    <ComponentSelectBox
                        setSelectedValue={storageSelector}
                        path={"api/common/allMemoryDrives"}
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
            {useCase === 'compatibility' && results && <CompatibilityTable results={results} />}
            {useCase === 'similarity' && results && <SimilarityTable results={results} />}
        </div>
    );
}

export default ChooseComponents;