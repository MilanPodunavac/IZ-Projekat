import { useState } from "react";
import axios from 'axios';
//import Swal from "sweetalert2";
import MultiSelectBox from "./MultiSelectBox";

const CauseOfFailure = () => {
    const [causes, setCauses] = useState([]);
    const [symptoms, setSymptoms] = useState([]);
    const [isPending, setIsPending] = useState(false);
    const [results, setResults] = useState();

    const onSubmit = async (e) => {
        e.preventDefault();
        const bayes = {
            "computerSymptomsList": symptoms,
            "malfunctionCausesList": causes
        };
        setIsPending(true);
        axios.post(axios.defaults.baseURL + 'api/Bayes', bayes).then(res => {
            setResults(res.data.results);
            setIsPending(false);
        }).catch((err) => { console.log(err) });
    }

    const causesSelector = causes => setCauses(causes);
    const symptomsSelector = symptoms => setSymptoms(symptoms);

    function getPercentageFromProbability(data){
        return data * 100;
    }

    return (
        <div className="container align-content: center display: flex align-items: center mt-5">
            <form style={{ maxWidth: "50%", margin: "auto" }}>
                <div className="mb-3">
                    <label className="form-label">Malfunction causes</label>
                    <MultiSelectBox
                        selectedValue={causes}
                        setSelectedValue={causesSelector}
                        path={"api/Bayes/malfunctionCauses"}
                        message={""}
                        propertyName={'malfunctionCausesList'} />
                </div>
                <div className="mb-3">
                    <label className="form-label">Computer symptoms</label>
                    <MultiSelectBox
                        selectedValue={symptoms}
                        setSelectedValue={symptomsSelector}
                        path={"api/Bayes/computerSymptoms"}
                        message={"computerSymptomsList"}
                        propertyName={'computerSymptomsList'} />
                </div>
                <div className="mb-3">
                    {!isPending &&
                        <span className="right">
                            <button disabled={causes.length == 0 && symptoms.length == 0} to="#" onClick={(e) => onSubmit(e)} type="submit" className="btn btn-primary">Submit</button>
                        </span>
                    }
                    {isPending && <label>Waiting for results...</label>}
                </div>
            </form>
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

export default CauseOfFailure;