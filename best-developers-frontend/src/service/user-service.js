import api, { genericErrorValidator } from "./api";
import { singUp } from "./auth-service";
import { setUser } from "./user-information-manager";

export const saveUser = async (name, password, email) => {
        
    try{
        await api.post("/users", {name, password, email});    
        
        await doSingIn(email, password);
        
    }catch(error){
        genericErrorValidator(error);
    }
                    
}

const doSingIn = async (email, password) =>{

    try{
        await singUp(email, password);
    }catch(error){
        throw `Login error. Caused by: ${error}`
    }
}


export const updateUser = async (id, name, password, email) => {

    try{
        await api.put("/users", {id, name, password, email})
        
        setUser({id, name, email});
        
    }catch(error){
        console.log(error)
        genericErrorValidator(error);
    }
}

export const deleteUserById = async (userId) => {
    try{
        await api.delete(`/users/${userId}`)
    }catch(error){
        genericErrorValidator(error);
    }
}

export const getUserById = async (userId) => {
    return await api.get(`/users/${userId}`)
}

const UserService = {
    saveUser,
    updateUser,
    deleteUserById,
    getUserById
}

export default UserService;