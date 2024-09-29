import useDataStore from "./index.js";

const useData = useDataStore()

const hasAuth = (perm) => {
    let perms = useData.perms;
    return perms.indexOf(perm) >= 0
}
export default hasAuth