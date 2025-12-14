package dao;

import java.util.ArrayList;
import java.util.List;
import model.Space;

public class SpaceDAO {
    public List<Space> getAllSpaces() {
        List<Space> list = new ArrayList<>();
        list.add(new Space(1, "Tầng 1 - Khu thường", "Không gian thoải mái cho gia đình", "https://20sfvn.com/wp-content/uploads/2022/09/thiet-ke-nha-hang-lau-nuong.jpeg"));
        list.add(new Space(2, "Tầng 2 - Khu VIP", "Không gian riêng tư, sang trọng", "https://20sfvn.com/wp-content/uploads/2022/09/thiet-ke-nha-hang-lau-nuong.jpeg"));
        list.add(new Space(3, "Sân vườn ngoài trời", "Thưởng thức BBQ ngoài trời", "https://20sfvn.com/wp-content/uploads/2022/09/thiet-ke-nha-hang-lau-nuong.jpeg"));
        return list;
    }
}