package backend.JDA.modelo;


public enum Sabor {
	SPICY,
	VEGETARIAN,
	GLUTEN_FREE,
	DAIRY_FREE,
	NUT_FREE;

	public static Sabor fromString(String raw) {
		return switch (raw.toLowerCase()) {
			case "isspicy" -> SPICY;
			case "isvegetarian" -> VEGETARIAN;
			case "isglutenfree" -> GLUTEN_FREE;
			case "isdairyfree" -> DAIRY_FREE;
			case "isnutfree" -> NUT_FREE;
			default -> throw new IllegalArgumentException("Atributo desconocido: " + raw);
		};
	}
}

