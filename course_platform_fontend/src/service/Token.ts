import storage from "./storage"
const TOKEN_KEY = "access_token";
export const Token = {
    
    setToken: (token: string) => {
        storage.set(TOKEN_KEY,token);
    },
    getToken: () : string=> {
        return storage.get(TOKEN_KEY)
    }
}