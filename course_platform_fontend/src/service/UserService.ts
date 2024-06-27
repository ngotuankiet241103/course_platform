import axios from "axios";
import { removeDiacritics } from "../utils/FormatStr";
import { env } from "../utils/env";
import { UserInfo } from "../types/UserInfo";
import requestApi from "./api";

class UserService {
    constructor(){
    }
    async generateImageByName(name: string){
        try {
            console.log(name);
            
            const value = removeDiacritics(name);
            const length = value.indexOf("+") > 0 ? 2 : 1;
            const background = "FF5733";
            const api = `${env.VITE_API_AVATAR}/?api_key=${env.VITE_API_KEY}`;
            const response = await axios.get(`${api}&name=${value}&background_color=${background}&image_size=128&char_limit=${length}&is_bold=true&font_size=0.6`,{ responseType: 'blob' })
            const data = await new File([response.data],"data",  { type: "image/png" });
            const urlCreator = window.URL || window.webkitURL;
            console.log(urlCreator.createObjectURL(data));
            
            return urlCreator.createObjectURL(data);
            
        } catch (error) {
            console.log(error);
            
        }
    }
    async getInfo() : Promise<UserInfo |null>{
        try {
            const response = await requestApi("/users/info","GET");
            if(response.status == 200){
                const result : UserInfo = response.data.result;
                console.log(result);
                
                return result;
            }
        } catch (error) {
            console.log(error);
            
        }
        return null;
    }
}
export default UserService;