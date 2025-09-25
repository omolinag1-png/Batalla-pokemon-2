import java.util.*;


class InvalidChoiceException extends Exception {
    public InvalidChoiceException(String message) {
        super(message);
    }
}

// Excepción para fallos de ataque
class AttackMissedException extends Exception {
    public AttackMissedException(String message) {
        super(message);
    }
}

abstract class Pokemon {
    protected String nombre;
    protected int hp;
    protected int poderEspecial;

    public Pokemon(String nombre, int hp, int poderEspecial) {
        this.nombre = nombre;
        this.hp = hp;
        this.poderEspecial = poderEspecial;
    }

    public String getNombre() { return nombre; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(0, hp); }

    // Métodos abstractos (se implementan en subclases)
    public abstract void atacar(Pokemon rival) throws AttackMissedException;
    public abstract void defender();

    public boolean estaVivo() {
        return hp > 0;
    }
}


class Mewtwo extends Pokemon {
    public Mewtwo() { super("Mewtwo", 100, 95); }
    public void atacar(Pokemon rival) throws AttackMissedException {
        if (new Random().nextInt(100) < 20) throw new AttackMissedException(nombre + " falló su Psicorayo!");
        int dano = new Random().nextInt(15) + 20;
        rival.setHp(rival.getHp() - dano);
        System.out.println(nombre + " usa Psicorayo! " + rival.getNombre() + " recibe " + dano + " de daño.");
    }
    public void defender() {
        System.out.println(nombre + " crea un escudo psíquico y reduce el daño.");
    }
}

class Charizard extends Pokemon {
    public Charizard() { super("Charizard", 100, 85); }
    public void atacar(Pokemon rival) throws AttackMissedException {
        if (new Random().nextInt(100) < 15) throw new AttackMissedException(nombre + " falló su Llamarada!");
        int dano = new Random().nextInt(20) + 25;
        rival.setHp(rival.getHp() - dano);
        System.out.println(nombre + " lanza Llamarada! " + rival.getNombre() + " recibe " + dano + " de daño.");
    }
    public void defender() {
        System.out.println(nombre + " se protege con un muro de fuego.");
    }
}

class Snorlax extends Pokemon {
    public Snorlax() { super("Snorlax", 120, 90); }
    public void atacar(Pokemon rival) throws AttackMissedException {
        if (new Random().nextInt(100) < 25) throw new AttackMissedException(nombre + " falló su Hiperrayo!");
        int dano = new Random().nextInt(25) + 10;
        rival.setHp(rival.getHp() - dano);
        System.out.println(nombre + " usa Hiperrayo! " + rival.getNombre() + " recibe " + dano + " de daño.");
    }
    public void defender() {
        System.out.println(nombre + " se duerme y recupera un poco de energía (+10 HP).");
        this.setHp(this.getHp() + 10);
    }
}

class Pikachu extends Pokemon {
    public Pikachu() { super("Pikachu", 90, 80); }
    public void atacar(Pokemon rival) throws AttackMissedException {
        if (new Random().nextInt(100) < 10) throw new AttackMissedException(nombre + " falló su Impactrueno!");
        int dano = new Random().nextInt(10) + 20;
        rival.setHp(rival.getHp() - dano);
        System.out.println(nombre + " usa Impactrueno! " + rival.getNombre() + " recibe " + dano + " de daño.");
    }
    public void defender() {
        System.out.println(nombre + " esquiva ágilmente el ataque.");
    }
}

class Gengar extends Pokemon {
    public Gengar() { super("Gengar", 95, 88); }
    public void atacar(Pokemon rival) throws AttackMissedException {
        if (new Random().nextInt(100) < 18) throw new AttackMissedException(nombre + " falló su Bola Sombra!");
        int dano = new Random().nextInt(15) + 18;
        rival.setHp(rival.getHp() - dano);
        System.out.println(nombre + " usa Bola Sombra! " + rival.getNombre() + " recibe " + dano + " de daño.");
    }
    public void defender() {
        System.out.println(nombre + " se oculta entre las sombras.");
    }
}


class Charmander extends Pokemon {
    private String[] ataques = {"Llamarada Infernal", "Garra Dragón"};
    private String[] defensas = {"Escudo Ígneo", "Evasión Fantasma"};

    public Charmander() { super("Charmander", 100, 100); }

    public void atacar(Pokemon rival) {
        String ataque = ataques[new Random().nextInt(ataques.length)];
        int dano = new Random().nextInt(15) + 25;
        rival.setHp(rival.getHp() - dano);
        System.out.println(nombre + " ataca con " + ataque + "! " + rival.getNombre() + " recibe " + dano + " de daño.");
    }

    public void defender() {
        String defensa = defensas[new Random().nextInt(defensas.length)];
        System.out.println(nombre + " se protege con " + defensa + "!");
        System.out.println(nombre + " aprovecha la defensa y contraataca causando 20 de daño!");
    }
}


public class JuegoPokemonPOO {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        Map<Integer, Pokemon> disponibles = new HashMap<>();
        disponibles.put(1, new Mewtwo());
        disponibles.put(2, new Charizard());
        disponibles.put(3, new Snorlax());
        disponibles.put(4, new Pikachu());
        disponibles.put(5, new Gengar());

        try {
            System.out.println("¡Bienvenido al mundo Pokémon!");
            System.out.print("Ingresa tu nombre, Maestro Pokémon: ");
            String nombre = sc.nextLine();

            System.out.println("\nHola " + nombre + "! Elige tu Pokémon guía:");
            for (Map.Entry<Integer, Pokemon> entry : disponibles.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue().getNombre());
            }

            System.out.print("Opción: ");
            int choice = sc.nextInt();
            if (!disponibles.containsKey(choice)) {
                throw new InvalidChoiceException("Elección inválida, no existe ese Pokémon.");
            }

            Pokemon jugador = disponibles.get(choice);
            Pokemon rival = new Charmander();

            System.out.println("\nHas elegido a " + jugador.getNombre() + "!");
            System.out.println("Tu rival es el Ingeniero David Álvarez con " + rival.getNombre() + "!");


            while (jugador.estaVivo() && rival.estaVivo()) {
                try {
                    jugador.atacar(rival);
                } catch (AttackMissedException e) {
                    System.out.println(e.getMessage());
                }

                if (!rival.estaVivo()) break;


                if (new Random().nextBoolean()) {
                    rival.atacar(jugador);
                } else {
                    rival.defender();
                    jugador.setHp(jugador.getHp() - 20);
                }

                System.out.println("HP " + jugador.getNombre() + ": " + jugador.getHp() +
                        " | HP " + rival.getNombre() + ": " + rival.getHp());
                System.out.println("---------------------------------------------------");

                try { Thread.sleep(1200); } catch (InterruptedException e) { }
            }

            System.out.println("\n💥 " + jugador.getNombre() + " ha sido derrotado...");
            System.out.println("👨‍💻 Ingeniero David Álvarez dice: \"Nunca subestimes el poder de un ingeniero.\"");

        } catch (InvalidChoiceException e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
