package grupoLem.appGestion.service;

import grupoLem.appGestion.model.Room;
import grupoLem.appGestion.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements IRoomService{
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> rooms() {
        List<Room> rooms = this.roomRepository.findAll();
        return rooms;
    }

    @Override
    public Room findById(Integer idRoom) {
        Room room = this.roomRepository.findById(idRoom).orElse(null);
        return room;
    }

    @Override
    public Room save(Room room) {
        return this.roomRepository.save(room);
    }

    @Override
    public void deleteById(Integer idRoom) {
        this.roomRepository.deleteById(idRoom);
    }

}
