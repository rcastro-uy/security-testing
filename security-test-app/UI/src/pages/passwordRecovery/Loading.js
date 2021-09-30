// https://hackernoon.com/step-by-step-guide-to-create-3-different-types-of-loading-screens-in-react-lu2633nd
import "./Loading.css"

export default function Loading(){
    return (
    <div className="spinner">
        <span>Cargando...</span>
        <div className="half-spinner"></div>
    </div>
    );
}
