package tk.myanimes.db;

public interface DbObject<BaseObj> {

    void serialize(BaseObj obj);

    BaseObj deserialize();

}
