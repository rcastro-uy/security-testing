import "./PasswordReset.styles.css"
import { useState , useEffect} from "react";
import { Link , useParams } from "react-router-dom"
import { useFormFields } from "./useFormFields"
import LoaderButton from "./LoaderButton"
import { FormGroup, FormControl, FormLabel } from "react-bootstrap";
import { changePassword, recoverPassword } from "../../api/passwordRecoveryService";

export default function ResetPasswordConfirm() {

    const [confirmado, setConfirmado] = useState(false);
    const [isConfirmando, setIsConfirmando] = useState(false);
    const { id } = useParams();

    const [campos, handleOnChange] = useFormFields({
        password: "",
        confirmarPassword: "",
    });

    function validarPasswordForm() {
        return (
          campos.password.length > 0 &&
          campos.password === campos.confirmarPassword
        );
    }

    async function handleConfirmarClick(event) {
        event.preventDefault();
        console.log(id);
        setIsConfirmando(true);
        try {
          await changePassword(
            id,
            campos.password
          );
          setConfirmado(true);
        } catch (error) {
          setIsConfirmando(false);
        }
    }

    function renderConfirmacionForm() {
        return (
          <form onSubmit={handleConfirmarClick}>
            {/*<FormGroup controlId="code">
              <FormLabel>Confirmation Code</FormLabel>
              <FormControl autoFocus type="tel" value={campos.code} onChange={handleOnChange} />
              <FormText>
                Please check your email ({campos.email}) for the confirmation code.
              </FormText>
            </FormGroup>
        <hr />*/}
            <FormGroup controlId="password">
              <FormLabel>Nuevo Password</FormLabel>
              <FormControl type="password" value={campos.password} onChange={handleOnChange} />
            </FormGroup>
            <FormGroup controlId="confirmarPassword">
              <FormLabel>Confirmar Password</FormLabel>
              <FormControl type="password" value={campos.confirmarPassword} onChange={handleOnChange} />
            </FormGroup>
            <LoaderButton block type="submit" isLoading={isConfirmando} disabled={!validarPasswordForm()}>
              Confirm
            </LoaderButton>
          </form>
        );
    }
    
    function renderMensajeDeSuceso() {
        return (
          <div className="success">
            <p>Password reestablecido con suceso.</p>
            <p>
              <Link to="/">
                Clic acá para volver al formulario de login.
              </Link>
            </p>
          </div>
        );
    }

    return (
        <div className="ConfirmPassword">
            {!confirmado ? renderConfirmacionForm() : renderMensajeDeSuceso()}
        </div>
    );
}