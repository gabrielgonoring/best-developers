import api, { genericErrorValidator } from "./api";

export const getDevelopers= async(size, page) =>{
    try{
        const {data} =  await api.get(`/developers?size=${size}&page=${page}`);
        return data;
    }catch(error){
        genericErrorValidator(error)
    }
}