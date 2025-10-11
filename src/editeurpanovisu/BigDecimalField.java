package editeurpanovisu;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Classe de remplacement pour jfxtras.labs.scene.control.BigDecimalField
 * qui n'est plus disponible dans les versions récentes de JFXtras.
 * 
 * Cette classe étend TextField et fournit une validation pour les BigDecimal.
 * 
 * @author Laurent Lang (Migration Java 25)
 */
public class BigDecimalField extends TextField {
    
    private final DecimalFormat format;
    private BigDecimal minValue = null;
    private BigDecimal maxValue = null;
    private final ObjectProperty<BigDecimal> number = new SimpleObjectProperty<>(BigDecimal.ZERO);
    
    /**
     * Constructeur par défaut
     */
    public BigDecimalField() {
        this(new DecimalFormat("#,##0.00"));
    }
    
    /**
     * Constructeur avec format personnalisé
     * @param format Format décimal à utiliser
     */
    public BigDecimalField(DecimalFormat format) {
        super();
        this.format = format;
        setupTextField();
    }
    
    /**
     * Constructeur avec valeur initiale
     * @param initialValue Valeur initiale
     */
    public BigDecimalField(BigDecimal initialValue) {
        this();
        setNumber(initialValue);
    }
    
    /**
     * Configure le TextField avec validation
     */
    private void setupTextField() {
        // Pattern pour valider les nombres décimaux (accepte , et . comme séparateurs)
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\[.,][0-9]*)?");
        
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };
        
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        this.setTextFormatter(textFormatter);
        
        // Validation à la perte de focus
        this.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                validateAndFormat();
            }
        });
    }
    
    /**
     * Valide et formate la valeur
     */
    private void validateAndFormat() {
        try {
            String text = getText().replace(',', '.');
            if (text != null && !text.isEmpty()) {
                BigDecimal value = new BigDecimal(text);
                
                // Vérifier les limites
                if (minValue != null && value.compareTo(minValue) < 0) {
                    value = minValue;
                }
                if (maxValue != null && value.compareTo(maxValue) > 0) {
                    value = maxValue;
                }
                
                number.set(value);
                setText(format.format(value));
            } else {
                number.set(BigDecimal.ZERO);
                setText(format.format(BigDecimal.ZERO));
            }
        } catch (Exception e) {
            // En cas d'erreur, réinitialiser à 0
            number.set(BigDecimal.ZERO);
            setText(format.format(BigDecimal.ZERO));
        }
    }
    
    /**
     * Obtient la propriété du nombre
     * @return La propriété ObjectProperty
     */
    public ObjectProperty<BigDecimal> numberProperty() {
        return number;
    }
    
    /**
     * Obtient la valeur BigDecimal
     * @return La valeur ou null si invalide
     */
    public BigDecimal getNumber() {
        return number.get();
    }
    
    /**
     * Définit la valeur BigDecimal
     * @param value La nouvelle valeur
     */
    public void setNumber(BigDecimal value) {
        if (value != null) {
            number.set(value);
            setText(format.format(value));
        } else {
            number.set(BigDecimal.ZERO);
            setText("");
        }
    }
    
    /**
     * Définit la valeur minimale
     * @param minValue Valeur minimale
     */
    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }
    
    /**
     * Définit la valeur maximale
     * @param maxValue Valeur maximale
     */
    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }
    
    /**
     * Obtient le format utilisé
     * @return Le format décimal
     */
    public DecimalFormat getFormat() {
        return format;
    }
}
