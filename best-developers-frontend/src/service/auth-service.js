import api, { genericErrorValidator } from "./api";
import { login } from "./auth-session-manager";
import { setUser } from "./user-information-manager";

import { getUserById } from "./user-service";

export const singUp = async (email, password) => {
    
    try{
        const {data} = await api.post('/auth', {email, password})
        login(data.token)

        await setUserInformations(data.userId);
    }catch(error){
        genericErrorValidator(error);
    }
    
}

const setUserInformations = async  (userId) => {
    
    const {data} = await getUserById(userId);
    setUser(data);
}