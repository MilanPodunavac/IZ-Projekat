import MultiSelectBox from "./MultiSelectBox";

const CpuForm = () => {

    return (
        <form style={{ maxWidth: "50%", margin: "auto" }}>
            <MultiSelectBox
                        className="form-control"
                        //selectedValue={componentType}
                        //setSelectedValue={componentTypeSelector}
                        path={"api/common/componentTypes"}
                        message={""}
                        propertyName={'componentTypesList'} 
                        singleSelect={true}/>
        </form>
    );
}

export default CpuForm;