import { useState } from "react";
import axios from 'axios';
//import Swal from "sweetalert2";
import ComponentSelectBox from "./ComponentSelectBox";
import SimilarityTable from "./SimilarityTable";
import CompatibilityTable from "./CompatibilityTable";

const ChooseComponents = ({useCase}) => {
    const [isPending, setIsPending] = useState(false);
    const [results, setResults] = useState();
    const [motherboard, setMotherboard] = useState();
    const [psu, setPsu] = useState();
    const [cpu, setCpu] = useState();
    const [gpu, setGpu] = useState();
    const [ram, setRam] = useState();

    const cpuSelector = selected => setCpu(selected[0]);
    const gpuSelector = selected => setGpu(selected[0]);
    const ramSelector = selected => setRam(selected[0]);
    const motherboardSelector = selected => setMotherboard(selected[0]);
    const psuSelector = selected => setPsu(selected[0]);

    const onSubmit = async (e) => {
        e.preventDefault();
        if(useCase==='compatibility'){
            //TODO: submit to compatibility endpoint
        }else{
            //TODO: submit to similar PCs endpoint
        }
        /*const bayes = {
            "computerSymptomsList": symptoms,
            "malfunctionCausesList": causes
        };
        setIsPending(true);
        axios.post(axios.defaults.baseURL + 'api/Bayes', bayes).then(res => {
            setResults(res.data.results);
            setIsPending(false);
        }).catch((err) => { console.log(err) });*/
    }

    function getPercentageFromProbability(data) {
        return data * 100;
    }

    function isSubmitDisabled() {
        return true;
    }

    return (
        <div className="container align-content: center display: flex align-items: center mt-5">
            <form style={{ maxWidth: "50%", margin: "auto" }}>
                <div className="mb-3">
                    <label className="form-label">Choose motherboard:</label>
                    <ComponentSelectBox
                        setSelectedValue={motherboardSelector}
                        path={"api/common/allMotherboards"}
                        message={""}
                        propertyName={'componentShortDtoList'}
                        singleSelect={true} />
                </div>
                <div className="mb-3">
                    <label className="form-label">Choose power supply unit:</label>
                    <ComponentSelectBox
                        setSelectedValue={psuSelector}
                        path={"api/common/allPsu"}
                        message={""}
                        propertyName={'componentShortDtoList'}
                        singleSelect={true} />
                </div>
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
            {useCase==='compatibility' && <CompatibilityTable/>}
            {useCase==='similarity' && <SimilarityTable/>}
        </div>
    );
}

export default ChooseComponents;