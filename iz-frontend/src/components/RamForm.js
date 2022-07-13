import { useState } from "react";
import ComponentSelectBox from "./ComponentSelectBox";
import axios from 'axios';
import RamTable from "./RamTable";

const RamForm = () => {
    const [motherboard, setMotherboard] = useState();
    const [isPending, setIsPending] = useState(false);
    const [results, setResults] = useState();

    const motherboardSelector = selected => setMotherboard(selected[0]);

    const onSubmit = async (e) => {
        e.preventDefault();
        setIsPending(true);
        const dto = {
            "mbName": motherboard,
        };
        axios.post(axios.defaults.baseURL + 'api/Ram', dto).then(res => {
            console.log(res.data)
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
                    {!isPending &&
                        <span className="right">
                            <button disabled={isSubmitDisabled()} to="#" onClick={(e) => onSubmit(e)} type="submit" className="btn btn-primary">Submit</button>
                        </span>
                    }
                    {isPending && <label>Waiting for results...</label>}
                </div>
            </form>
            {
                results && <RamTable results={results}/>
            }
        </>
    );
}

export default RamForm;