import { useState } from "react";
import ComponentSelectBox from "./ComponentSelectBox";

const StorageForm = () => {
    const [motherboard, setMotherboard] = useState();

    const motherboardSelector = selected => setMotherboard(selected[0]);

    return (
        <>
        <hr/>
        <form style={{ maxWidth: "50%", margin: "auto" }}>
            <div className="mb-3">
                <label className="form-label">Choose motherboard:</label>
                <ComponentSelectBox
                    setSelectedValue={motherboardSelector}
                    path={"api/common/allMotherboards"}
                    message={""}
                    propertyName={'componentShortDtoList'} 
                    singleSelect={true}/>
            </div>
        </form>
        </>
    );
}

export default StorageForm;