import { BrowserRouter, Redirect, Route, Switch } from "react-router-dom";
import HomePage from "./pages/home";
import SingInPage from "./pages/sing-in-page";
import SingUpPage from "./pages/sing-up-page";
import UpdateUserPage from "./pages/update-user-page";
import { isAuthenticated } from "./service/auth-session-manager";


const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route
      {...rest}
      render={props =>
        isAuthenticated() ? (
          <Component {...props} />
        ) : (
          <Redirect to={{ pathname: "/", state: { from: props.location } }} />
        )
      }
    />
  );

const PageRoutes = () =>  (
    <BrowserRouter>
        <Switch>
            <Route exact path = "/" component={SingInPage}/>
            <Route path = "/sing-in" component={SingInPage}/>
            <Route exact path = "/sing-up" component={SingUpPage}/>
            <PrivateRoute path = "/home" component={HomePage} />
            <PrivateRoute path = "/update-user" component={UpdateUserPage} />
        </Switch>
    </BrowserRouter>
)

export default PageRoutes;