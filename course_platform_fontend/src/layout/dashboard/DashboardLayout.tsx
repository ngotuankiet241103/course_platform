import { Suspense } from 'react';
import Sidebar from './Sidebar';
import { Outlet } from 'react-router-dom';
import KeycloakService from '../../service/Keycloak';
import useTheme from '../../hooks/useTheme';
import FrameDashboard from './FrameDashboard';

const DashboardLayout = () => {
    const keycloakService = KeycloakService.getInstance();
    const {isExpand} = useTheme();
    if(!keycloakService.getKeyCloak()){
        keycloakService.init();
       
    }
    return (
        <div className='flex gap-2'>
            {isExpand &&  <Sidebar></Sidebar>}
            <div>
            
            <FrameDashboard>
                <Suspense fallback={<h1>Loading...</h1>}>
                    <Outlet></Outlet>
                </Suspense>
            </FrameDashboard>
            </div>
        </div>
    );
};

export default DashboardLayout;