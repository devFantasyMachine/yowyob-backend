import { Tenant } from "@/types/tenant";
import { createSlice } from "@reduxjs/toolkit";
import { RootState } from "../../store";



interface TenantState {

    tenantState?: Tenant
}


// Initial state
const initialState: TenantState = {tenantState: undefined};

// Actual Slice
export const tenantSlice = createSlice({
    name: "tenant",
    initialState,
    reducers: {
        // Action  
        setTenant(state, action) {
            state.tenantState = action.payload;
        },
    },

})


export const { setTenant } = tenantSlice.actions;
export const getTenant = (state: RootState) => state.tenant.tenantState;
export default tenantSlice.reducer;
