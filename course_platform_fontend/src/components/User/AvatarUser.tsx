import { useRef } from "react";
import { UserInfo } from "../../types/UserInfo";
import { useQuery } from "react-query";
import UserService from "../../service/UserService";


const AvatarUser = ({info: {name,avatar}}: & {info: UserInfo}) => {
    const imageRef = useRef<HTMLImageElement>(null);

    const {isLoading,data: image} = useQuery(
        {
            queryKey: ['avatar-user'],
            queryFn: async () => {
                if(avatar) return;
                
                    try {
                        const userService = new UserService();
                        const response =  await userService.generateImageByName(name);
                        return response;
                        
                    } catch (error) {
                        console.log(error);
                        
                    }
                
            },
           staleTime: 5000 * 1000,
           cacheTime: 5000 * 1000,
           
            
        }
    )
    if(isLoading) return "Loading...";
    console.log(image);
    
    
    return (
        <div className={`flex gap-2 items-center  p-2 cursor-pointer transition-all rounded-lg`} >
              {
                image && <img ref={imageRef} src={avatar || image} className='w-[32px] h-[32px] rounded-full'/>
              }
            <h4>{name}</h4>
        </div>
    );
};

export default AvatarUser;