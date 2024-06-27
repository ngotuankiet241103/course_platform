import Keycloak from "keycloak-js";
import { env } from "../utils/env";
import storage from "./storage";
import { Token } from "./Token";


class KeycloakService{
    private static keycloackService : KeycloakService;
    private keyCloak:  Keycloak
    private url = env.VITE_BASE_URL_KEYCLOACK
    private realm = env.VITE_BASE_URL_REALMS;
    private clientID = env.VITE_BASE_URL_CLIENTID;
  
    public static getInstance() : KeycloakService {
        if(!this.keycloackService){
            this.keycloackService = new KeycloakService();
        }
        return this.keycloackService;
    }
    getKeyCloak() {
       
        return this.keyCloak;
    }
    constructor(){
        this.keyCloak = this.getKeyCloak();
        
    }
    async init(){
        if(this.keyCloak) return;
        if(!this.keyCloak){
            this.keyCloak = new Keycloak(
                {
                    clientId: this.clientID,
                    realm: this.realm,
                    url: this.url
                }
            )
            
            const authentication = await this.keyCloak?.init({
                onLoad: "login-required"
            })
            
            if(authentication){
                this.keyCloak.token && Token.setToken(this.keyCloak.token);
                
            }
        }
        
        
        
        
    }
    getToken(){
        return this.keyCloak?.token;
    }
    async getUserInfo(){
        
        // const tokenParse : number | undefined=  this.keyCloak?.tokenParsed?.exp;
        // console.log(tokenParse);
        
        // console.log( this.keyCloak?.isTokenExpired(tokenParse));
        
        
        // const user : UserProfile | undefined = await this.keyCloak?.loadUserProfile();
        // console.log(user);
        
        //     return user ;
        
        
        
    }
    isAuthenticated() {
        return this.keyCloak?.authenticated;
    }
    login(){
        return this.keyCloak?.login();
    }
    
   
}
export default KeycloakService;