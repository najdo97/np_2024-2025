import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class SeatTakenException extends Exception {
    public SeatTakenException() {
    }
}

class SeatNotAllowedException extends Exception {
    public SeatNotAllowedException() {
    }
}

enum SectorType {
    NEUTRAL,
    HOME,
    AWAY
}

class Seat {
    private int seatId;
    private boolean isOccupied;

    public Seat(int seatId, boolean isOccupied) {
        this.seatId = seatId;
        this.isOccupied = isOccupied;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}

class Sector {
    private String sectorId;
    private SectorType sectorType;
    private List<Seat> seats;
    private int capacity;

    public Sector(String sectorId, int capacity) {
        this.sectorId = sectorId;
        this.capacity = capacity;

        this.sectorType = SectorType.NEUTRAL;
        this.seats = new ArrayList<>();

        for (int i = 0; i < capacity; i++) {
            this.seats.add(new Seat(i, false));
        }
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public SectorType getSectorType() {
        return sectorType;
    }

    public void setSectorType(SectorType sectorType) {
        this.sectorType = sectorType;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

class Stadium {
    private String stadiumName;
    private List<Sector> sectors;


    public Stadium(String stadiumName) {
        this.stadiumName = stadiumName;
        this.sectors = new ArrayList<>();
    }

    void createSectors(String[] sectorNames, int[] sizes) {
        for (int i = 0; i < sectorNames.length; i++) {
            this.sectors.add(new Sector(sectorNames[i], sizes[i]));
        }
    }

    void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        SectorType sectorType = SectorType.NEUTRAL;

        switch (type) {
            case 1:
                sectorType = SectorType.HOME;
            case 2:
                sectorType = SectorType.AWAY;
        }
//todo - losho e kastira sectorType
        
        for (int i = 0; i < sectors.size(); i++) {
            if (sectors.get(i).getSectorId().equals(sectorName)) {
                if (sectors.get(i).getSectorType().equals(sectorType) || sectors.get(i).getSectorType().equals(SectorType.NEUTRAL)) {
                    if (sectors.get(i).getSeats().get(seat - 1).isOccupied() == false) {
                        sectors.get(i).getSeats().get(seat - 1).setOccupied(true);
                        sectors.get(i).setSectorType(sectorType);
                        break;

                    } else {
                        throw new SeatTakenException();
                    }
                } else {
                    throw new SeatNotAllowedException();
                }

            }
        }
    }

    void showSectors() {
        List<Sector> sortedSectors = this.sectors
                .stream()
                .sorted((Comparator.comparing((Sector e1) -> e1.getCapacity() - e1.getSeats()
                        .stream()
                        .mapToInt(e -> e.isOccupied() ? 1 : 0).sum()))
                        .reversed().thenComparing(Sector::getSectorId)
                )
                .collect(Collectors.toList());

        sortedSectors.forEach(sector ->
                System.out.println((sector.getSectorId() + " - Free Seats: " + (sector.getCapacity() - sector.getSeats().stream().mapToInt(e -> e.isOccupied() ? 1 : 0).sum()))));
    }

}

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}
