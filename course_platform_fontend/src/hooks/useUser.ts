import UserService from '../service/UserService';
import { useQuery } from 'react-query';

const useUser = () => {
    const userService = new UserService();
    const {isLoading, data: profile} = useQuery({
        queryKey: ["user-info"],
        queryFn: userService.getInfo,
        staleTime: 1000 * 6000
    })
    return {
        isLoading,profile
    }
};

export default useUser;