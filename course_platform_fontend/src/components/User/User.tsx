
import AvatarUser from "./AvatarUser";
import useUser from "../../hooks/useUser";
import useTheme from "../../hooks/useTheme";


const User = () => {
    const {profile} = useUser();
    const {handleToggleExpand} = useTheme();
    
    
    return (
        <>
        {
            profile && 
            <div className='flex  justify-between relative'>
                <AvatarUser info={profile} ></AvatarUser>
                <div className='flex items-center gap-4'>
                    <span><i className="fa-regular fa-bell"></i></span>
                    <span onClick={handleToggleExpand}><i className="fa-solid fa-table-columns cursor-pointer hover:opacity-80"></i></span>
                </div>
               
            </div>
            
        }
       
        </>
       
    );
};

export default User;