import { getTenant, setTenant } from "@/lib/stores/features/tenants/tenantSlice";
import { useAppDispatch, useAppSelector } from "@/lib/stores/hooks";
import { fetchTenant } from "@/services/tenant-service";
import { ObjectUtils } from "@/utils/object-utils";
import { useQuery } from "react-query";



export const useTenant = () => {

    const dispatch = useAppDispatch();
	const tenantState = useAppSelector(getTenant);

    const loadTenant = async () => {

		if (ObjectUtils.isUndefined(tenantState)) {

			return await fetchTenant()
				.then(tenant => {

					dispatch(setTenant(tenant));
					return tenant;
				})
		}
		else {

			return tenantState!;
		}

	}

    return useQuery(["tenant"], () => loadTenant(),
        {
            retry: true,
            retryDelay: 2000, 
        }
    );
}




