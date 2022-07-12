import { useState } from "react";
import axios from 'axios';
//import Swal from "sweetalert2";
import MultiSelectBox from "./MultiSelectBox";
import MotherboardForm from "./MotherboardForm";
import PsuForm from "./PsuForm";
import RamForm from "./RamForm";
import CpuForm from "./CpuForm";
import GpuForm from "./GpuForm";
import StorageForm from "./StorageForm";

const ComponentRecommendation = () => {
    const [componentType, setComponentType] = useState([]);
    const [isPending, setIsPending] = useState(false);
    const [results, setResults] = useState();

    const componentTypeSelector = type => setComponentType(type[0]);
    const resultsSelector = resultsRetVal => setResults(resultsRetVal);

    function getPercentageFromProbability(data){
        return data * 100;
    }

    return (
        <div className="container align-content: center display: flex align-items: center mt-5">
            <form style={{ maxWidth: "50%", margin: "auto" }}>
                <div className="mb-3">
                    <label className="form-label">Choose component for upgrade</label>
                    <MultiSelectBox
                        className="form-control"
                        selectedValue={componentType}
                        setSelectedValue={componentTypeSelector}
                        path={"api/common/componentTypes"}
                        message={""}
                        propertyName={'componentTypesList'} 
                        singleSelect={true}/>
                </div>
                {/*<div className="mb-3">
                    {!isPending &&
                        <span className="right">
                            <button disabled={ symptoms.length == 0} to="#" onClick={(e) => onSubmit(e)} type="submit" className="btn btn-primary">Submit</button>
                        </span>
                    }
                    {isPending && <label>Waiting for results...</label>}
                </div>*/}
            </form>
            {componentType === 'Motherboard' && <MotherboardForm/>}
            {componentType === 'PowerSupplyUnit' && <PsuForm/>}
            {componentType === 'RandomAccessMemory' && <RamForm/>}
            {componentType === 'CentralProcessingUnit' && <CpuForm/>}
            {componentType === 'GraphicsProcessingUnit' && <GpuForm/>}
            {componentType === 'Storage' && <StorageForm/>}
            {
                results &&
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Malfunction</th>
                            <th scope="col">Probability</th>
                        </tr>
                    </thead>
                    <tbody>
                        {results && (results.map((result, index) => (
                            <tr key={index}>
                                <th scope="row">{index+1}</th>
                                <td>{result.malfunction}</td>
                                <td>{getPercentageFromProbability(result.probability)} %</td>
                            </tr>
                        )))}
                    </tbody>
                </table>
            }
        </div>
    );
}

export default ComponentRecommendation;